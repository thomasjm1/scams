import json

from flask import Blueprint, request, Response, logging, current_app
from flask_jwt_extended import create_access_token, jwt_required, get_jwt_identity, JWTManager

authentication = Blueprint('authentication', __name__, url_prefix='/api/authentication')


@authentication.route('/login', methods=['POST'])
def login():
    if not request.is_json:
        return Response(json.dumps({"msg": "Missing JSON in request"}), status=400, mimetype='application/json')
    identity = request.json.get('identity', None)
    secret = request.json.get('secret', None)
    if not identity:
        return Response(json.dumps({"msg": "Missing identity parameter"}), status=400, mimetype='application/json')
    if not secret:
        return Response(json.dumps({"msg": "Missing secret parameter"}), status=400, mimetype='application/json')
    if identity != 'test' or secret != 'test':
        return Response(json.dumps({"msg": "Bad identity or secret"}), status=401, mimetype='application/json')
    access_token = create_access_token(identity=identity)
    return Response(json.dumps({'access_token': access_token}), status=200, mimetype='application/json')


@authentication.route('/register', methods=['POST'])
def register():
    if not request.is_json:
        return Response(json.dumps({"msg": "Missing JSON in request"}), status=400, mimetype='application/json')
    identity = request.json.get('identity', None)
    secret = request.json.get('secret', None)
    if not identity:
        return Response(json.dumps({"msg": "Missing identity parameter"}), status=400, mimetype='application/json')
    if not secret:
        return Response(json.dumps({"msg": "Missing secret parameter"}), status=400, mimetype='application/json')
    if identity != 'test' or secret != 'test':
        # Create identity/secret in database for all future associations
        return Response(json.dumps({"msg": "Bad identity or secret"}), status=401, mimetype='application/json')
    access_token = create_access_token(identity=identity)
    return Response(json.dumps({'access_token': access_token}), status=200, mimetype='application/json')


@authentication.route('/protected', methods=['GET'])
@jwt_required
def protected():
    current_user = get_jwt_identity()
    return Response(json.dumps({'logged_in_as': current_user}), status=200, mimeType='application/json')

@authentication.errorhandler(500)
def server_error(e):
    logging.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500
