from utilities.RandomUtility import RandomUtility


class Config(object):
    DEBUG = False
    TESTING = False
    DATABASE_URI = 'sqlite://:memory:'


class ProductionConfig(Config):
    DATABASE_URI = 'mysql://user@localhost/foo'
    JWT_SECRET_KEY = RandomUtility.random_string(64)


class DevelopmentConfig(Config):
    DEBUG = True


class TestingConfig(Config):
    TESTING = True
    JWT_SECRET_KEY = 'helloworldvalue'
