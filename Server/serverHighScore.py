from flask import Flask
from flask import jsonify
from flask_restful import Resource, Api
from flask import request
import os

app = Flask(__name__)
api = Api(app)

import json
def getScoreInFile():
    scores = []
    
    with open("highScore.txt") as f:
        for line in f:
            res = line.split()
            scores.append(line)
        f.close()
    return json.dumps(scores)

def addScoreToFile(score, name):
    with open("highScore.txt", "a") as f:
        if os.path.getsize("highScore.txt") < 0:
            f.write(name + " " + score)
        else:
            f.write(name + " " + score + "\n")
    f.close()

@app.route('/', methods=['GET', 'POST'])
def highScore():
    if(request.method == 'GET'):
        result = getScoreInFile()
        return jsonify(result)
    addScoreToFile(request.form['score'], request.form['username'])
    return json.dumps({'success':True}), 200

if __name__ == '__main__':
     app.run(host='192.168.1.23', port='5002')

