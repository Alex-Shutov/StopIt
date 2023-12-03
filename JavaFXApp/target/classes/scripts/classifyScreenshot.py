from nudenet import NudeClassifier
import os
import glob
import re

dir_path = os.path.dirname(os.path.realpath(__file__))

def get_latest_screenshots() -> str:
    roaming_path = os.path.join(os.path.expanduser('~'), 'AppData', 'Roaming', 'StopIt')
    list_of_files = glob.glob(f'{roaming_path}/*')
    # latest_file = max(list_of_files, key=os.path.getctime)
    return list_of_files

def strip_answer(answer : dict) -> dict:
    abs_path = list(answer.keys())[0]
    relpath =  os.path.relpath(abs_path, start=os.curdir)
    return {relpath :  f"Safety degree: {list(answer.values())[0]['safe']:.3f}"}

def calculate_average_unsafe(data):
    total_unsafe = sum(item['unsafe'] for item in data.values())
    count = len(data)

    if count > 0:
        average_unsafe = total_unsafe / count
        return average_unsafe
    else:
        return 0  # Возвращаем 0, если нет данных

if __name__ == '__main__':
    classifier = NudeClassifier()
    answer = classifier.classify(get_latest_screenshots(), batch_size=5)
    answer = calculate_average_unsafe(answer)
    # answer = strip_answer(answer)
    print(answer)
