import datetime
from enum import IntEnum

from peewee import *

from model.messages.MessageBaseModel import MessageBaseModel


class Message(MessageBaseModel):
    sender = CharField(null=False)
    recipient = CharField(null=False)
    content = CharField(null=False)
    state = IntegerField(null=False)
    created = DateTimeField(default=datetime.datetime.now)
    received = DateTimeField(default=datetime.datetime.now)
    recipient_received = DateTimeField(default=datetime.datetime.now)


class MessageState(IntEnum):
    PENDING = 0
    RECEIVED = 1