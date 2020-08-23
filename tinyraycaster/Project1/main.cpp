#include <iostream>
#include <fstream>
#include <vector>
#include <cstdint>
#include <cassert>
//#define _USE_MATH_DEFINES;
//#include <math.h>
//these two will allow for math constants, such as pi(M_PI)
using namespace std;

const float PI = 3.14159265358979323846;

uint32_t pack_color(const uint8_t R, const uint8_t G, const uint8_t B, const uint8_t A = 255)
{
	return (A << 24) + (B << 16) + (G << 8) + R;
}

void unpack_color(const uint32_t& color, uint8_t& R, uint8_t& G, uint8_t& B, uint8_t& A)
{
	R = (color >> 0) & 255;
	G = (color >> 8) & 255;
	B = (color >> 16) & 255;
	A = (color >> 24) & 255;
}

void drop_ppm_image(const string filename, const vector<uint32_t>& image, const size_t width, const size_t height)
{
	assert(image.size() == width * height);
	ofstream ofs(filename, ios::binary);
	ofs << "P6\n" << width << " " << height << "\n255\n";
	for (size_t i = 0; i < height * width; i++)
	{
		uint8_t R, G, B, A;
		unpack_color(image[i], R, G, B, A);
		ofs << static_cast<char>(R) << static_cast<char>(G) << static_cast<char>(B);
	}
	ofs.close();
}

void draw_rectangle(vector<uint32_t>& img, const size_t img_w, const size_t img_h, const size_t x, const size_t y, const size_t w, size_t h, const uint32_t color)
{
	assert(img.size() == img_w * img_h);
	for (size_t X = 0; X < w; X++)
	{
		for (size_t Y = 0; Y < h; Y++)
		{
			size_t cx = x + X;
			size_t cy = y + Y;
			//assert(cx < img_w&& cy < img_h);
			if (cx >= img_w || cy >= img_h)continue;
			img[cx + cy * img_w] = color;
		}
	}
}

int main() { //the actual thing that runs
	const size_t win_w = 1024;//image width
	const size_t win_h = 512;//image width
	vector<uint32_t> framebuffer(win_h * win_h, pack_color(255,255,255));//image itself

	const size_t map_w = 16;
	const size_t map_h = 16;
	const char map[] = "1111333333331111"\
		"2000000000000001"\
		"2000000222220001"\
		"2000001000000001"\
		"1000001002221111"\
		"1000004000000001"\
		"1000211110000001"\
		"1000100022211001"\
		"1000100010000001"\
		"1000100020011111"\
		"1000000020000001"\
		"3000000020000001"\
		"1000000010000001"\
		"1011111110000001"\
		"1000000000000001"\
		"1113333333311111";

	assert(sizeof(map) == map_w * map_h + 1);

	float player_x = 3.456;
	float player_y = 2.345;
	float player_a = 1.523;
	const float fov = PI / 3;
	//draw gradient
	//for (size_t y = 0; y < win_h; y++)
	//{
	//	for (size_t x = 0; x < win_w; x++)
	//	{
	//		uint8_t R = 255 * y / float(win_h);
	//		uint8_t G = 255 * x / float(win_w);
	//		uint8_t B = 0;
	//		framebuffer[x + y * win_w] = pack_color(R, G, B);
	//	}
	//}
	//end draw gradient
	//const size_t rect_w = win_w / map_w;
	const size_t rect_w = win_w / (map_w * 2);
	const size_t rect_h = win_h / map_h;
	//draw map
	for (size_t Y = 0; Y < map_h; Y++)
	{
		for (size_t X = 0; X < map_w; X++)
		{
			if (map[X + Y * map_w] == ' ') continue;
			else if (map[X + Y * map_w] == '0')
			{
				size_t rect_x = X * rect_w;
				size_t rect_y = Y * rect_h;
				draw_rectangle(framebuffer, win_w, win_h, rect_x, rect_y, rect_w, rect_h, pack_color(0, 0, 0));
				//continue;
			}
			else
			{
				size_t rect_x = X * rect_w;
				size_t rect_y = Y * rect_h;
				draw_rectangle(framebuffer, win_w, win_h, rect_x, rect_y, rect_w, rect_h, pack_color(255, 255, 255));
			}
		}
	}
	//end draw map
	//draw player
	draw_rectangle(framebuffer, win_w, win_h, player_x * rect_w, player_y * rect_h, 5, 5, pack_color(255, 255, 0));
	//end draw player
	//raycast
	for (size_t i = 0; i < win_w/2; i++)
	{
		float angle = player_a - fov / 2 + fov * i / float(win_w/2);

		for (float t = 0; t < 20; t += .05)
		{
			float cx = player_x + t * cos(angle);
			float cy = player_y + t * sin(angle);
			size_t pix_x = cx * rect_w;
			size_t pix_y = cy * rect_h;
			framebuffer[pix_x + pix_y * win_w] = pack_color(212, 208, 200);
			
			if (map[int(cx) + int(cy) * map_w] != '0')break;
			size_t column_height = win_h / t;
			draw_rectangle(framebuffer, win_w, win_h, win_w / 2 + i, win_h / 2 - column_height / 2, 1, column_height, pack_color(255,0,0));
			break;
		}
	}
	//end raycast
	drop_ppm_image("./out.ppm", framebuffer, win_w, win_h);

	return 0;
}