from ultralytics import YOLO
import yaml
from typing import List


# A class to detect grocery items using a YOLO model
class GroceryItemDetector:
    def __init__(self, model_path, data_path, confidence):
        self.model = YOLO(model_path)
        self.data = self.load_data(data_path)
        self.conf = confidence

    # Loads the data configuration from a YAML file
    def load_data(self, data_path):
        with open(data_path, 'r') as file:
            return yaml.safe_load(file)

    # Extracts and returns a list of detected item names from the results
    def get_list_of_detected_items(self, results):
        detected_items = set()
        for result in results:
            for box in result.boxes:
                detected_items.add(result.names[box.cls.item()])
        return list(detected_items)

    # Detects items in an image and prints the detected items
    def detect(self, image_path) -> List[str]:
        results = self.model(image_path, conf=self.conf, data=self.data)
        detected_items = self.get_list_of_detected_items(results)
        return detected_items


def main():
    model_path = 'models/model_1.pt'  # Path to the pretrained model
    data_path = 'data.yaml'  # Path to the data configuration YAML file
    conf = 0.5  # Set the thresholds for probability that the cell contains an object

    # Path to the image for detection
    image_path = 'data/Retail-Cart-fast_mov-43_jpg.rf.6940ef907e87f0956850883ecdc982f0.jpg'

    detector = GroceryItemDetector(model_path, data_path, conf)
    result = detector.detect(image_path)
    print(result)

if __name__ == "__main__":
    main()

