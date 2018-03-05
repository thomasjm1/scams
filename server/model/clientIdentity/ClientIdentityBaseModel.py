from peewee import Proxy, Model, PrimaryKeyField

database_proxy = Proxy()


class ClientIdentityBaseModel(Model):
    id = PrimaryKeyField

    class Meta:
        database = database_proxy
