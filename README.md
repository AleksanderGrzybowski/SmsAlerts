# SmsAlerts

## What is this?

This is a small app I used for monitoring info.kolejeslaskie.com webpage, when I was travelling by train a lot a few years ago. Delays and train/infrastructure problems in Silesian Railways happen pretty often, so I made an app that monitors and scrapes this informational webpage. If it finds an occurence of any given preset keywords (which are basically my destination station names) it sends SMS to given phone number with alert's title.

In fact KS have this type of service available, at least they advertised it like a year ago, but why not reinvent something if you can? :D

## Technology stack

* Spring, Spock for testing
* AWS SDK for SNS integration
* custom integration with bramkasms.pl for backup SMS sending service
* React for frontend

![Screenshot 1](screenshot.png)
