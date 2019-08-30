#version 400 core

in vec2 pass_textureCoords;
out vec4 out_color;

uniform sampler2D textureSampler;

void main(void) {
    /* Returns the color of the pixel at the texture; samples at coordinates */
    out_color = texture(textureSampler, pass_textureCoords);
}