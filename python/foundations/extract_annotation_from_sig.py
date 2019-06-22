from inspect import signature


def circle_area_with_type(r: int = 80) -> float:
    return 3.14 * r * r


def circle_area_with_comment(r: "circle's radius") -> "circle's area":
    return 3.14 * r * r


if __name__ == '__main__':
    type_sig = signature(circle_area_with_type)
    comment_sig = signature(circle_area_with_comment)

    print("type's return annotation: ", type_sig.return_annotation)        # type's annotation:  <class 'float'>
    print("comment's return annotation: ", comment_sig.return_annotation)  # comment's annotation:  circle's area

    for param in type_sig.parameters.values():
        note = repr(param.annotation)
        print(f'{note}: {param.name} = {param.default}')  # <class 'int'>: r = 80

    for param in comment_sig.parameters.values():
        note = repr(param.annotation)
        print(f'{note}: {param.name} = {param.default}')  # "circle's radius": r = <class 'inspect._empty'>
