## v1.0.0, 24-Dec-2020, First Public Release:

- Created two skins:

    - Techno
    - Pastorg

- Implemented various syntax higlighting:

    - Highlight.js (JavaScript)
    - Rouge (Ruby)
    - Pygments (Python)

- Implemented simple higlighting API.

- Implemented code table.

- Implemented tag compensator.

### Technical Notes:

```
git log --pretty=oneline --abbrev-commit | wc -l
183

cloc src/main/java/ src/main/resources/*.properties src/main/resources/static/ src/main/resources/templates/
      42 text files.
      42 unique files.
       4 files ignored.

github.com/AlDanial/cloc v 1.82  T=0.09 s (425.0 files/s, 41552.9 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                            29            375            771           1513
CSS                              6             17             21            792
HTML                             2              0              4            159
JavaScript                       1              4              1             58
-------------------------------------------------------------------------------
SUM:                            38            396            797           2522
-------------------------------------------------------------------------------
```
