import axios from 'axios';

export function healthcheck() {
  return axios.get('/health');
}

export function fetchMessages() {
  return axios.get('/api/messages');
}

export function fetchAlerts() {
  return axios.get('/api/alerts');
}

export function fetchAccountBalance() {
  return axios.get('/api/balance');
}
