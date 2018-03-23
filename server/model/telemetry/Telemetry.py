import datetime
from peewee import *

from model.BaseModel import BaseModel


class Telemetry(BaseModel):
    data_type = CharField(null=False)
    content = CharField(null=False)
    created = DateTimeField(default=datetime.datetime.now)
    received = DateTimeField(default=datetime.datetime.now)
