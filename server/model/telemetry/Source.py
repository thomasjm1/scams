
import datetime
from peewee import *

from model.BaseModel import BaseModel


class Source(BaseModel):
    value = TextField(null=False)
    source_type = CharField(null=False)
    verifications = IntegerField(null=False)
    created = DateTimeField(default=datetime.datetime.now)