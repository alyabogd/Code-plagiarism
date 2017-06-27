#include<iostream>

int main() {
    int n;
    std::cin >> n;

    int arr[n];
    for(int i = 0; i < n; ++i) {
        std::cin >> arr[i];
    }

    for(int i = 0; i < n; i++) {
        for(int j = 1; j < n - i; ++j) {
            if (arr[j] > arr[j - 1]) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
    }

     for(int i = 0; i < n; ++i) {
        std::cout << arr[i] << " ";
     }
     std::cout << std::endl;
}

