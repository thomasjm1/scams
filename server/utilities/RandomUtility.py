import random
import string


class RandomUtility(object):

    @staticmethod
    def random_string(size):
        character_set = string.ascii_uppercase + string.digits
        return ''.join(random.choice(character_set) for character in range(size))
