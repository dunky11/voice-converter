import React, {PureComponent} from "react";
import {
    StyleSheet, 
    View,
    NativeModules
} from  "react-native";
import { withRouter } from "react-router-native";
import { IconButton, withTheme, Text, Colors } from "react-native-paper";
const { AudioRecorder } = NativeModules;

function msToString (s) {
    let ms = String(s % 1000).slice(0, 2);
    let secs = String(Math.floor((s - ms) / 1000));
    if (secs.length == 1) {
        secs = "0" + secs;
    }
    if (ms.length == 1) {
        ms = "0" + ms;
    }
  
    return secs + ':' + ms;
  }

class RecordAudio extends PureComponent {

    constructor(props) {
        super(props);
        this.state = { 
            isRecording: false, 
            timeElapsed: "00:00", 
            micBars: 0, 
            audio: null 
        };
    }

    startRecording = async () => {
        AudioRecorder.start(success => {
            if(success) {
                this.setState({isRecording: success});
            }
        });
    }

    stopRecording = () => {
        this.setState({isRecording: false, audio: "smth"});
        AudioRecorder.stop();
    }

    displayMicBars = () => {
        const {micBars} = this.state;
        const {theme, isDarkMode} = this.props;
        const unusedBarColor = isDarkMode ? theme.colors.surface : "grey";
        const bars = [];
        for (let i = 1; i < 11; i++) {
          bars.push(
              <View
                key={i}
                style={{
                  height: 80,
                  width: "6%",
                  marginRight: i !== 10 ? 8 : 0,
                  backgroundColor: micBars >= i ? theme.colors.primary : unusedBarColor,
                }}
              ></View>,
          );
        }
        return bars;
      };

    startConversion = () => {
        const {history } = this.props;
        history.push("/convert-audio");
    }

    render() {
        const {isRecording, timeElapsed, audio} = this.state;
        const {theme} = this.props;
        return (
            <View style={styles.container}>
                <View style={{marginTop: 32}}>
                    <Text style={{fontSize: 50}}>{timeElapsed}</Text>
                </View>
                <View style={{display: "flex", flexDirection: "row"}}>
                    {this.displayMicBars()}
                </View>
                <View style={{display: "flex", alignItems: "center", flexDirection:"row"}}>
                    <IconButton
                        icon="harddisk"
                        size={48}
                    />
                    <IconButton
                        icon={isRecording ? "stop-circle" : "microphone"}
                        size={80}
                        color={theme.colors.primary}
                        onPress={isRecording ? this.stopRecording : this.startRecording}
                        animated 
                    />
                    <IconButton
                        icon="check"
                        size={48}
                        onPress={this.startConversion}
                        disabled={!audio}
                        color={audio ? theme.colors.primary: null}
                    />
                </View>
            </View>
        );
    }
}

export default withRouter(withTheme(RecordAudio));

const styles = StyleSheet.create({
    container: { 
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        paddingTop: 16,
        paddingBottom: 16,
        flex: 1,
    },
});