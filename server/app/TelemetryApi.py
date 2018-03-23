import json

from flask import Blueprint, logging, Response
from flask_jwt_extended import jwt_required, get_jwt_identity

from model.BaseModel import database_proxy

telemetry = Blueprint('telemetry', __name__, url_prefix='/api/telemetry')


@telemetry.before_request
def before_request():
    if database_proxy.is_closed():
        database_proxy.connect()


@telemetry.after_request
def after_request(response):
    database_proxy.close()
    return response


@telemetry.route('/', methods=['POST'])
@jwt_required
def create():
    identifier = get_jwt_identity()
    return Response(json.dumps({'identifier': identifier, 'operation': 'send'}), status=200, mimetype='application/json')


@telemetry.errorhandler(500)
def server_error(e):
    logging.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500
