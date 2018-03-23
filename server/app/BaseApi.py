import json

from playhouse import shortcuts
from werkzeug.local import LocalProxy
from flask import current_app, Blueprint, Response

from model.messages.Message import Message, MessageState
from repositories.MessageRepository import MessageRepository
from utilities.DateTimeEncoder import DateTimeEncoder

logger = LocalProxy(lambda: current_app.logger)

base = Blueprint('base', __name__, url_prefix='')


@base.route('/')
def index():

    return Response(json.dumps({'status': 'ok'}), status=200, mimetype='application/json')