import React, { Component } from 'react';
import Spinner from './static/Spinner';
import { fetchMessages } from './api';
import { Table } from 'react-bootstrap';

class MessagesTable extends Component {

  constructor(props) {
    super(props);

    this.state = {loaded: false, messages: []};
  }

  componentDidMount() {
    fetchMessages()
      .then(({data}) => this.setState({loaded: true, messages: data}));
  }

  renderRows = () => this.state.messages.map(message =>
    <tr key={message.id}>
      <td component="th">
        {message.status}
      </td>
      <td>{message.text}</td>
    </tr>
  );

  render() {
    if (!this.state.loaded) return <Spinner/>;

    return (
      <Table striped>
        <thead>
        <tr>
          <td>Status</td>
          <td>Text</td>
        </tr>
        </thead>
        <tbody>
        {this.renderRows()}
        </tbody>
      </Table>
    );
  }
}

export default MessagesTable;