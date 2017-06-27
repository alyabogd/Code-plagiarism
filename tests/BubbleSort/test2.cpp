#include<iostream>

// the same, unless some whitespace characters, comments and variable names
int main() {
    int len;

    std::cin >> len;

    int data[len];
    for(int i = 0; i < len; ++i) {
            // read array
        std::cin >> data[i];
    }

    // start bubble sorting
    for(int i = 0; i < len; i++) {
        for(int j = 1; j < len - i; ++j) {
            if (data[j] > data[j - 1]) {
                // swap elements
                int saved = data[j];
                data[j] = data[j - 1];
                data[j - 1] = saved;
            }
        }
    }

     for(int i = 0; i < len; ++i) {
            // write array
        std::cout << data[i] << " ";
     }
     std::cout << std::endl;
}


