import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from 'axios';
import Spinner from './static/Spinner';
import { fetchMessages } from './api';

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
  },
});


class MessagesTable extends Component {
 
  constructor(props) {
    super(props);

    this.state = {loaded: false, messages: []};
  }

  componentDidMount() {
    fetchMessages()
      .then(({data}) => this.setState({loaded: true, messages: data}));
  }

  render() {
    if (!this.state.loaded) return <Spinner />;

    const rows = this.state.messages.map(message =>
      <TableRow key={message.id}>
        <TableCell component="th" scope="row">
          {message.status}
        </TableCell>
        <TableCell>{message.text}</TableCell>
      </TableRow>
    );
    return (
      <Paper className={this.props.classes.root}>
        <Table className={this.props.classes.table}>
          <TableHead>
            <TableRow>
              <TableCell>Status</TableCell>
              <TableCell>Text</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows}
          </TableBody>
        </Table>
      </Paper>
    );
  }
}

export default withStyles(styles)(MessagesTable);