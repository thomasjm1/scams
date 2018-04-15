import datetime
from peewee import *

from model.BaseModel import BaseModel


class Parameters(BaseModel):
    content = TextField(null=False)
    created = DateTimeField(default=datetime.datetime.now)
