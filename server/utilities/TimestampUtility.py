import datetime


class TimestampUtility(object):

    @staticmethod
    def now():
        return datetime.datetime.now()

    @staticmethod
    def parse(timestamp):
        return datetime.datetime.fromtimestamp(timestamp)
