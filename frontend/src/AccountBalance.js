import React, { Component } from 'react';
import { fetchAccountBalance } from './api';

class AccountBalance extends Component {

  constructor(props) {
    super(props);

    this.state = {loaded: false, balance: 0.0};
  }

  componentDidMount() {
    fetchAccountBalance()
      .then(({data}) => this.setState({loaded: true, balance: data.accountBalance}));
  }

  render() {
    return this.state.loaded ? (
      <span>{this.state.balance} PLN</span>
    ) : '...';
  }
}

export default AccountBalance;