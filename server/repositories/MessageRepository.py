import logging
from model.messages.Message import Message
from repositories.PersistenceError import PersistenceError


class MessageRepository(object):
    logger = logging.getLogger(__name__)

    def create_message(self, message):
        self.logger.debug("Inserting message: {}".format(str(message)))
        rows = message.save()
        if rows == 1:
            self.logger.debug("Successfully inserted message: {}".format(str(message)))
            return message
        else:
            self.logger.debug("Insert of message: {} returned {}".format(str(message), str(rows)))
            raise PersistenceError("Insert of message: {} returned {}".format(str(message), str(rows)))

    def retrieve_messages(self):
        self.logger.debug("Retrieving all message")
        return Message.select()
