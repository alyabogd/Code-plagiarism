#include<iostream>
#include<vector>

// change array -> vector
int main() {
    int n;
    std::cin >> n;

    std::vector<int> arr;
    for(int i = 0; i < n; ++i) {
        int d;
        std::cin >> d;
        arr.push_back(d);
    }

    for(int i = 0; i < n; i++) {
        for(int j = 1; j < n - i; ++j) {
            if (arr[j] > arr[j - 1]) {
                int t = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = t;
            }
        }
    }

     for(int i = 0; i < n; ++i) {
        std::cout << arr[i] << " ";
     }
     std::cout << std::endl;
}


