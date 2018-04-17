import calendar
import datetime
import time


class TimestampUtility(object):

    @staticmethod
    def now():
        return datetime.datetime.now()

    @staticmethod
    def parse(timestamp):
        return datetime.datetime.fromtimestamp(timestamp)

    @staticmethod
    def timestamp():
        return int(time.time())

    @staticmethod
    def to_timestamp(input_datetime):
        return calendar.timegm(input_datetime.utctimetuple())
