from enum import IntEnum
from peewee import *
from model.clientIdentity.ClientIdentityBaseModel import ClientInentityBaseModel

class ClientInentity(ClientInentityBaseModel):
    identifier = CharField(null=False)
    profile = CharField(null=False)
    recovery = CharField(null=False)
    status = IntegerField(null=False)


class ClientInentityState(IntEnum):
    NOTINUSE = 0
    INUSE = 1
