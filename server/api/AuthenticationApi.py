import json

from flask import Blueprint, request, Response, logging, g
from flask_jwt_extended import create_access_token, jwt_required

from api.BaseApi import authentication_required, logger
from api.ResponseWrapper import ResponseWrapper
from model.BaseModel import database_proxy
from model.identities.Identity import Identity, IdentityState
from repositories.IdentityRepository import IdentityRepository

authentication = Blueprint('authentication', __name__, url_prefix='/api/authentication')


@authentication.before_request
def before_request():
    if database_proxy.is_closed():
        database_proxy.connect()


@authentication.after_request
def after_request(response):
    database_proxy.close()
    return response


@authentication.route('/login', methods=['POST'])
def login():
    if not request.is_json:
        return Response(ResponseWrapper.wrap("none", "authentication.login", {'message': "Expected JSON"}), status=400,
                        mimetype='application/json')
    json_data = request.get_json()
    identifier = json_data['identifier']
    secret = json_data['secret']
    if not identifier:
        return Response(ResponseWrapper.wrap("none", "authentication.login", {'message': "Missing identifier"}),
                        status=400, mimetype='application/json')
    if not secret:
        return Response(ResponseWrapper.wrap("none", "authentication.login", {'message': "Missing secret"}), status=400,
                        mimetype='application/json')
    identity_repository = IdentityRepository()
    identity = identity_repository.retrieve_identity_by_identifier(identifier)
    access_token = create_access_token(identity=identity.identifier)
    return Response(
        ResponseWrapper.wrap(identifier, "authentication.login", {'access_token': access_token}),
        status=200,
        mimetype='application/json')


@authentication.route('/register', methods=['POST'])
def register():
    if not request.is_json:
        return Response(ResponseWrapper.wrap("none", "authentication.register", {'message': "Expected JSON"}),
                        status=400, mimetype='application/json')

    json_data = request.get_json()
    identifier = json_data['identifier']
    secret = json_data['secret']
    if not identifier:
        return Response(ResponseWrapper.wrap("none", "authentication.register", {'message': "Missing identifier"}),
                        status=400, mimetype='application/json')
    if not secret:
        return Response(ResponseWrapper.wrap("none", "authentication.register", {'message': "Missing secret"}),
                        status=400, mimetype='application/json')
    profile = json_data['profile']
    recovery = json_data['recovery']
    identity_repository = IdentityRepository()
    identity = identity_repository.create_identity(Identity(
        identifier=identifier,
        secret=secret,
        profile=profile,
        recovery=recovery,
        status=IdentityState.IN_USE
    ))
    return Response(ResponseWrapper.wrap(identifier, "authentication.register", identity), status=200,
                    mimetype='application/json')


@authentication.route('/protected', methods=['GET'])
@jwt_required
@authentication_required
def protected():
    identifier = g.identifier
    return Response(json.dumps({'logged_in_as': identifier}), status=200, mimetype='application/json')


@authentication.errorhandler(500)
def server_error(e):
    logger.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500