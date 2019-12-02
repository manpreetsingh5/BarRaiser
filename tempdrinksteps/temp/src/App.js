import Repeatable from 'react-repeatable';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import React from 'react';
import './App.css';
import { Button, Container, Row, ProgressBar, Col, Tabs, Tab } from 'react-bootstrap';
import bottle_src from './img/vodka.svg';
import glass_src from './img/wine.svg';
import shaker_src from './img/shaker.svg';
import salt_src from './img/salt.svg';
import plate_src from './img/plate.svg';
import ice_src from './img/ice.svg';
import milk_src from './img/milk.svg';
import spoon_src from './img/spoon.svg';
import posed from 'react-pose';


const Bottle = posed.img({
  standing: { rotate: '0deg' },
  pouring: {
    rotate: '-165deg',
    transition: { duration: 400 }
  }
});

const PourLiquid = posed.div({
  standing: { height: '0' },
  pouring: {
    height: '150px',
    delay: 200,
  }
});

const PosedH5 = posed.h5({
  visible: { opacity: 1 },
  hidden: { opacity: 0 },
})

const PosedProgressBar = posed(React.forwardRef((props, ref) => <ProgressBar {...props} ref={ref} />))({
  visible: { opacity: 1 },
  hidden: { opacity: 0 },
});

class PourLiquidGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: 72,
      pressed: false,
      competed: false,
      hint: false,
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
        <Container>
          <Row className="mt-5">
            <Col sm={8}>
              <Row>
                <Col sm={3} className="mx-auto">
                  <Bottle className="front img-fluid test" src={bottle_src} alt={'bottle'} pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row className="pour-row">
                <Col sm={3} className="mx-auto">
                  <PourLiquid id="pour_liquid" className="mx-auto" pose={this.state.pressed ? 'pouring' : 'standing'} />
                </Col>
              </Row>
              <Row>
                <Col sm={3} className="mx-auto">
                  <img className="img-fluid" src={glass_src} alt={'glass'} />
                </Col>
              </Row>
            </Col>
            <Col sm={2} className="mx-auto">
              <PosedH5 pose={this.state.hint? 'visible' : 'hidden'}>Target: {this.state.target}oz</PosedH5>
              <h5>Current: {this.state.progress}oz</h5>
              <Row className="my-3 mt-5">
                <Repeatable
                  tag={Button}
                  variant="info"
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
                  POUR DRINK
                </Repeatable>
              </Row>
              <Row className="my-3">
                <Button variant="info"
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
                >COMPLETE</Button>
              </Row>
              <Row className="my-3">
                <Button variant="info"
                  onClick={() => {
                    this.setState({
                      hint: true,
                    });
                  }}
                >GET HINT</Button>
              </Row>
            </Col>
          </Row>
        </Container>
        <Row className="mt-5">
          <Col>
            <ProgressBar>
              <ProgressBar animated variant="info" now={this.state.progress} key={1} />
              <PosedProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} pose={this.state.hint ? 'visible' : 'hidden'} />
            </ProgressBar>
          </Col>
        </Row>
      </Container>
    );
  }
}

const PourSolid = posed.div({
  standing: { height: '0px' },
  pouring: {
    height: '200px',
    delay: 200,
  }
});

class PourSolidGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: 33,
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
            <ProgressBar>
              <ProgressBar animated now={this.state.progress} key={1} />
              <ProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} />
            </ProgressBar>
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
            <Bottle className="front img-fluid" src={salt_src} alt={'salt'} pose={this.state.pressed ? 'pouring' : 'standing'} />
          </Col>
        </Row>
        <Row className="pour-row">
          <Col sm={2} className="mx-auto">
            <PourSolid id="pour_solid" className="mx-auto" pose={this.state.pressed ? 'pouring' : 'standing'} />
          </Col>
        </Row>
        <Row>
          <Col sm={2} className="mx-auto">
            <img className="img-fluid" src={plate_src} alt={'plate'} />
          </Col>
        </Row>
      </Container>
    );
  }
}

const Shaker = posed.img({
  up: { y: 0 },
  down: { y: 100 }
});

class ShakeGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: 50,
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
            <ProgressBar>
              <ProgressBar animated now={this.state.progress} key={1} />
              <ProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} />
            </ProgressBar>
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
                  progress: Math.min(this.state.progress + 5, 100)
                })
                this.checkProgress();
              }}
              onHoldStart={() => {
              }}
              onHold={() => {
              }}
              onHoldEnd={() => {
              }}
              onRelease={() => {
                this.setState({
                  pressed: false,
                })
              }}
            >
              Shake
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
            <Shaker className="front img-fluid" src={shaker_src} alt={'shaker'} pose={this.state.pressed ? 'up' : 'down'} />
          </Col>
        </Row>
      </Container>
    );
  }
}

const Ingredient = posed.img({
  up: { y: 0 },
  down: {
    y: 100,
    transition: { duration: 50 },
  }
});

const FilledIngredient = posed.img({
  visible: { opacity: 1 },
  hidden: { opacity: 0 },
})

class FillGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: 20,
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
            <ProgressBar>
              <ProgressBar animated now={this.state.progress} key={1} />
              <ProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} />
            </ProgressBar>
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
                  progress: Math.min(this.state.progress + 5, 100)
                })
                this.checkProgress()
              }}
              onHoldStart={() => {
              }}
              onHold={() => {
              }}
              onHoldEnd={() => {
              }}
              onRelease={() => {
                this.setState({
                  pressed: false,
                })
              }}
            >
              Fill
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
          <Col sm={1} className="mx-auto">
            <Ingredient className="front img-fluid" src={ice_src} alt={'ice'} pose={this.state.pressed ? 'down' : 'up'} />
          </Col>
        </Row>
        <Row>
          <Col sm={3} className="mx-auto">
            <Row>
              <Col sm={12} className="mx-auto">
                <img className="img-fluid" src={glass_src} alt={'glass'} />
              </Col>
            </Row>
            <Row className="filled_ingredient_row">
              <Col sm={4} className="mx-auto">
                <FilledIngredient className="img-fluid" src={ice_src} alt={'ice'} pose={this.state.progress > 0 ? 'visible' : 'hidden'} />
              </Col>
            </Row>
          </Col>
        </Row>
      </Container>
    );
  }
}

const Spoon = posed.img({
  left: { x: -30 },
  right: { x: 30 }
})

class StirGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      progress: 0,
      target: 50,
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
            <ProgressBar>
              <ProgressBar animated now={this.state.progress} key={1} />
              <ProgressBar animated variant="danger" now={this.state.target - this.state.progress} key={2} />
            </ProgressBar>
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
              Stir
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
          <Col sm={3} className="mx-auto">
            <Row>
              <Col sm={8} className="mx-auto front">
                <Spoon className="img-fluid" src={spoon_src} alt={'spoon'} pose={this.state.progress % 2 === 0 ? 'left' : 'right'} />
              </Col>
            </Row>
            <Row className="glass_stir_row">
              <Col sm={12} className="mx-auto">
                <img className="img-fluid" src={milk_src} alt={'glass'} />
              </Col>
            </Row>
          </Col>
        </Row>
      </Container>
    );
  }
}

class MemoryGame extends React.Component {
  render() {
    return (
      <Container>

      </Container>
    );
  }
}

class App extends React.Component {
  render() {
    return (
      <Container>
        {/* Tabs are used just to put examples in one App.js --> real app will not have tabs/tab */}
        <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
          <Tab eventKey="pourliquid" title="Pour Liquid">
            <PourLiquidGame />
          </Tab>
          <Tab eventKey="shake" title="Shake">
            <ShakeGame />
          </Tab>
          <Tab eventKey="poursolid" title="Pour Solid">
            <PourSolidGame />
          </Tab>
          <Tab eventKey="fill" title="Fill with Ingredient">
            <FillGame />
          </Tab>
          <Tab eventKey="stir" title="Stir">
            <StirGame />
          </Tab>
          <Tab eventKey="memory" title="Recognition Game">
            <MemoryGame />
          </Tab>
        </Tabs>
      </Container>
    );
  }
}

export default App;
