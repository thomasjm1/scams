import json

from flask import Blueprint, logging, Response
from flask_jwt_extended import jwt_required, get_jwt_identity

from model.BaseModel import database_proxy

messages = Blueprint('messages', __name__, url_prefix='/api/messages')


@messages.before_request
def before_request():
    if database_proxy.is_closed():
        database_proxy.connect()


@messages.after_request
def after_request(response):
    database_proxy.close()
    return response


@messages.route('/', methods=['POST'])
@jwt_required
def send():
    identifier = get_jwt_identity()
    return Response(json.dumps({'identifier': identifier, 'operation': 'send'}), status=200, mimetype='application/json')


@messages.route('/', methods=['GET'])
@jwt_required
def receive():
    identifier = get_jwt_identity()
    return Response(json.dumps({'identifier': identifier, 'operation': 'receive'}), status=200, mimetype='application/json')


@messages.route('/', methods=['PUT'])
@jwt_required
def update():
    identifier = get_jwt_identity()
    return Response(json.dumps({'identifier': identifier, 'operation': 'update'}), status=200, mimetype='application/json')


@messages.errorhandler(500)
def server_error(e):
    logging.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500
