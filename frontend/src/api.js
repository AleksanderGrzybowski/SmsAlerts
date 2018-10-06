import axios from 'axios';

export function healthcheck() {
  return axios.get('/health');
}

export function fetchMessages() {
  return axios.get('/api/messages');
}

export function fetchAlerts(page, pageSize) {
  return axios.get(`/api/alerts?size=${pageSize}&page=${page}`);
}
