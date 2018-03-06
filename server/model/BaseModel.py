from peewee import Proxy, Model, PrimaryKeyField

database_proxy = Proxy()


class BaseModel(Model):
    id = PrimaryKeyField()

    class Meta:
        database = database_proxy
