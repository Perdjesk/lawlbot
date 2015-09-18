# lawlbot
The lawlbot is a IRC bot which provides lawls &amp; stuff

## IRC commands

`!lawl` reponds with a random line from `etc/lawls.txt`
```
<Jon> !lawl
<lawlbot> Jon: http://i.imgur.com/XkZhitA.jpg
```

`!roll [0-9]{1,2}d[0-9]{1,4}` rolls dices with given arguments.
```
<Jon> !roll 6d6
<lawlbot> Jon: : [1, 2, 5, 2, 6, 6]
```


## Deploy app to heroku

Install the heroku CLI and setup your login information: https://devcenter.heroku.com/articles/heroku-command

Clone this repository
```bash
git clone https://github.com/Perdjesk/lawlbot.git
```
Modifiy the configuration in `etc` folder.

Setup the heroku in `lawlbot` folder.
```bash
cd lawlbot
heroku create
git push heroku master
```


