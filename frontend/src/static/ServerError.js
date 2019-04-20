import React from 'react';
import { Alert } from 'react-bootstrap';

function ServerError() {

  return (
    <Alert variant="danger">
      <Alert.Heading> Something went wrong </Alert.Heading>
      <p>
        Please <a href="/">refresh the page</a> or try again later.
      </p>
    </Alert>
  );
}

export default ServerError;