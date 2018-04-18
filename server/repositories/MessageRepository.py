import logging
from model.messages.Message import Message
from repositories.PersistenceError import PersistenceError

"""
Contains Message table accesses.
"""


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

    def retrieve_messages_by_sender(self, identifier):
        self.logger.debug("Retrieving all messages from {}".format(identifier))
        return Message.select().where(Message.sender == identifier)

    def update_message(self, identifier, recipient_received, state):
        message = self.retrieve_message_by_identifier(identifier)
        message.recipient_received = recipient_received
        message.state = state
        updates = message.save()
        if updates == 1:
            self.logger.debug("Successfully updated message: {}".format(message.identifier))
            return message
        else:
            self.logger.debug("Update of message: {} returned {}".format(message.identifier, str(updates)))
            raise PersistenceError("Failed to update message: {}".format(message.identifier))

    def retrieve_message_by_identifier(self, identifier):
        self.logger.debug('Retrieving message: {}'.format(identifier))
        return Message.select().where(Message.identifier == identifier).get()
