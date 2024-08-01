attribute vec4 vPosition;
void main() {
    gl_Position = vPosition;
}

precision mediump float;
uniform vec4 vColor;
void main() {
    gl_FragColor = vColor;
}