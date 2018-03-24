import json

from utilities.DateTimeEncoder import DateTimeEncoder


class JsonUtility(object):

    @staticmethod
    def to_json(input_object):
        return json.dumps(input_object, cls=DateTimeEncoder)

    @staticmethod
    def from_json(input_object):
        return json.loads(input_object, cls=DateTimeEncoder)
