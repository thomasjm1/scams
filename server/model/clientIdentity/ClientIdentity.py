from enum import IntEnum
from peewee import *
from model.clientIdentity.ClientIdentityBaseModel import ClientIdentityBaseModel

class ClientIdentity(ClientIdentityBaseModel):
    identifier = CharField(null=False)
    profile = CharField(null=False)
    recovery = CharField(null=False)
    status = IntegerField(null=False)


class ClientIdentityState(IntEnum):
    NOTINUSE = 0
    INUSE = 1
