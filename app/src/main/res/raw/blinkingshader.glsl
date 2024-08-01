// Vertex Shader
attribute vec4 vPosition;
void main() {
    gl_Position = vPosition;
}

// Fragment Shader
#ifdef GL_ES
precision mediump float;
#endif
uniform float u_time;

void main() {
    gl_FragColor = vec4(abs(sin(u_time)), 0.0, 0.0, 1.0);
}