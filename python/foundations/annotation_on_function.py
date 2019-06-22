def circle_area_with_type(r: int = 80) -> float:
    return 3.14 * r * r


def circle_area_with_comment(r: "circle's radius") -> "circle's area":
    return 3.14 * r * r


if __name__ == '__main__':
    print(circle_area_with_type.__annotations__)     # {'r': <class 'int'>, 'return': <class 'float'>}
    print(circle_area_with_comment.__annotations__)  # {'r': "circle's radius", 'return': "circle's area"}

    print(circle_area_with_type())        # 20096.0
    print(circle_area_with_type(-1))      # 3.14
    print(circle_area_with_type(9.3234))  # 272.9469729384
