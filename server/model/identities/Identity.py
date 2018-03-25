from enum import IntEnum
from peewee import *
from playhouse.shortcuts import model_to_dict

from model.BaseModel import BaseModel
from utilities.JsonUtility import JsonUtility


class Identity(BaseModel):
    identifier = CharField(null=False)
    secret = CharField(null=False)
    profile = CharField(null=False)
    recovery = CharField(null=False)
    status = IntegerField(null=False)


class IdentityState(IntEnum):
    NOT_IN_USE = 0
    IN_USE = 1
