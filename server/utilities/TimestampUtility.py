import datetime


class TimestampUtility(object):

    @staticmethod
    def now():
        return datetime.datetime.now()
