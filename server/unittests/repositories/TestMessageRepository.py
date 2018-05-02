import os
import unittest

from model.messages.Message import Message, MessageState
from repositories.MessageRepository import MessageRepository
from unittests.repositories.DbTest import DbTest


class TestMessageRepository(DbTest):
    def setUp(self):
        self.repository = MessageRepository()

    def test_create_message(self):
        message = self.repository.create_message(
            Message(
                identifier="HelloWorld",
                sender="Jeremy",
                recipient="Jeremy",
                content="Hello World",
                state=MessageState.PENDING
            )
        )
        self.assertEqual(message.sender, 'Jeremy')

    def test_retrieve_message(self):
        message = self.repository.create_message(
            Message(
                identifier="HelloWorld",
                sender="Jeremy1",
                recipient="Jeremy",
                content="Hello World",
                state=MessageState.PENDING
            )
        )
        self.assertEqual(message.sender, 'Jeremy1')
        messages = self.repository.retrieve_messages()
        self.assertGreater(len(messages), 1)

    def test_retrieve_message_by_recipient(self):
        message = self.repository.create_message(
            Message(
                identifier="HelloWorld",
                sender="Jeremy1",
                recipient="Jeremy",
                content="Hello World",
                state=MessageState.PENDING
            )
        )
        self.assertEqual(message.sender, 'Jeremy1')
        message2 = self.repository.create_message(
            Message(
                identifier="HelloWorld",
                sender="Jeremy",
                recipient="Jeremy1",
                content="Hello World",
                state=MessageState.PENDING
            )
        )
        self.assertEqual(message2.recipient, 'Jeremy1')
        messages = self.repository.retrieve_messages_by_sender("Jeremy1")
        self.assertEqual(len(messages), 1)


if __name__ == '__main__':
    unittest.main()
