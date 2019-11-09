import Repeatable from 'react-repeatable';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import React from 'react';
import './App.css';
import { Button, Container, Row, ProgressBar, Col } from 'react-bootstrap';
import bottle_src from './img/vodka.svg';
import glass_src from './img/wine.svg';
import posed from 'react-pose';


const Bottle = posed.img({
  visible: {opacity: 1},
  hidden: {opacity: 0},
  standing: {rotate: '0deg'},
  pouring: {
    rotate: '-165deg',
    transition: {duration: 400}
  }
});

const Pour = posed.div({
  standing: {height: '0'},
  pouring: {
    height: '150px',
    delay: 200,
  }
});

class DrinkGame extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: 72,
      pressed: false,
      competed: false,
      drink_result: null,
    }
  }

  checkProgress() {
    if (!this.state.completed && this.state.progress > (this.state.target)) {
      this.setState({
        completed: true,
        drink_result: 'FAILED',
        progress: 0,
      })
    }
  }


  render() {
    return (
      <Container>
        <Row>
          <Col>
            <ProgressBar animated now={this.state.progress}>
            </ProgressBar>
            <div className="marker" style={{ right: (100 - this.state.target) + '%' }}>
            </div>
          </Col>
        </Row>
        <Row>
          <Col>
            <h4>Target: {this.state.target}</h4>
          </Col>
          <Col>
            <h4>Current: {this.state.progress}</h4>
          </Col>
        </Row>
        <Row>
          <Col>
            <Repeatable
              tag={Button}
              repeatDelay={0}
              repeatInterval={150}
              onPress={() => {
                this.setState({
                  pressed: true,
                })
              }}
              onHoldStart={() => {
              }}
              onHold={() => {
                this.setState({
                  progress: Math.min(this.state.progress + 1, 100)
                });
                this.checkProgress();
              }}
              onHoldEnd={() => {
              }}
              onRelease={() => {
                this.setState({
                  pressed: false,
                })
              }}
            >
              Pour Drink
          </Repeatable>
          </Col>
          <Col>
            <Button variant="success"
              onClick={() => {
                this.setState({
                  completed: true,
                });
                if (this.state.progress === this.state.target) {
                  alert('Congratulations')
                } else {
                  this.setState({
                    drink_result: 'FAILED',
                    progress: 0,
                  })
                }
              }}
            >Complete</Button>
          </Col>
        </Row>
        <Row>
          <h5>{this.state.drink_result}</h5>
        </Row>
        <Row className="mt-5">
          <Col sm={2} className="mx-auto">
            <Bottle className="front img-fluid" src={bottle_src} alt={'bottle'} pose={this.state.pressed ? 'pouring' : 'standing'}/>
          </Col>
        </Row>
        <Row className="pour-row">
          <Col sm={2} className="mx-auto">
            <Pour id="pour" className="mx-auto" pose={this.state.pressed ? 'pouring' : 'standing'} />
          </Col>
        </Row>
        <Row>
          <Col sm={2} className="mx-auto">
            <img className="img-fluid" src={glass_src} alt={'glass'}/>
          </Col>
        </Row>
      </Container>
    );
  }
}

class App extends React.Component {


  render() {
    return (
      <Container>
        <Row>
          <DrinkGame />
        </Row>
      </Container>
    );
  }
}

export default App;
