import datetime
import json

from flask import Blueprint, logging, Response, g, request
from flask_jwt_extended import jwt_required
from playhouse.shortcuts import model_to_dict

from api.BaseApi import authentication_required
from model.BaseModel import database_proxy
from model.messages.Message import Message, MessageState
from repositories.MessageRepository import MessageRepository
from utilities.JsonUtility import JsonUtility
from utilities.TimestampUtility import TimestampUtility

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
@authentication_required
def create():
    identifier = g.get('identifier', None)
    message_parameters = request.get_json()
    message_repository = MessageRepository()
    message = message_repository.create_message(Message(
        sender=identifier,
        recipient=message_parameters['recipient'],
        content=message_parameters['content'],
        state=MessageState.PENDING,
        created=message_parameters['created'],
        received=TimestampUtility.now(),
        recipient_received=None
    ))
    result = Response(JsonUtility.to_json({'identifier': identifier, 'operation': 'create', 'result': model_to_dict(message)}), status=200,
                      mimetype='application/json')
    return result


@messages.route('/', methods=['GET'])
@jwt_required
def retrieve():
    identifier = g.get('identifier', None)
    return Response(JsonUtility.to_json({'identifier': identifier, 'operation': 'retrieve'}), status=200,
                    mimetype='application/json')


@messages.route('/', methods=['PUT'])
@jwt_required
def update():
    identifier = g.get('identifier', None)
    return Response(JsonUtility.to_json({'identifier': identifier, 'operation': 'update'}), status=200,
                    mimetype='application/json')


@messages.errorhandler(500)
def server_error(e):
    logging.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500
