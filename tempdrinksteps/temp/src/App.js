import Repeatable from 'react-repeatable';
import cx from 'classnames';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import React from 'react';
import './App.css';
import { Button, Container, Row, ProgressBar, Col } from 'react-bootstrap';

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
              <div className="marker" style={{right: (100 - this.state.target) + '%'}}>
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
              btnStyle={cx({
                'default': !this.state.pressed,
                'info': this.state.pressed,
              })}
              repeatDelay={0}
              repeatInterval={150}
              onPress={() => {
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
                  if(this.state.progress === this.state.target){
                    alert('Congratulations')
                  }else{
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
