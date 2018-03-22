import main


def test_index():
    main.app.testing = True
    client = main.app.test_client()

    response = client.get('/', environ_base={'REMOTE_ADDR': '127.0.0.1'})
    assert response.status_code == 200
    assert '127.0' in response.data.decode('utf-8')