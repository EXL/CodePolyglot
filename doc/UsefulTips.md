Useful Tips
===========

## Find all files without line ending at the end

```shell script
find src/ doc/ util/ -type f -print0 | xargs -0 -L1 bash -c 'test "$(tail -c 1 "$0")" && echo "No new line at end of $0"'
```

This command should be run at the root of the project.

## Find trailing spaces on files

```shell script
grep -r '[[:blank:]]$' src/ doc/ util/
```
This command should be run at the root of the project.

## Find stored a static file in the Nginx cache

```shell script
grep -lr 'login.css' /var/nginx/cache/*
```

This command should be run on the server side with root user privileges.

## Add license header to files by name mask

```shell script
find . -name \*.java -exec sh -c "mv '{}' tmp && cp util/template/license_header_common '{}' && cat tmp >> '{}' && rm tmp" \;
find . -name \*.py -exec sh -c "mv '{}' tmp && cp util/template/license_header_other '{}' && cat tmp >> '{}' && rm tmp" \;
```

* [license_header_common](../util/template/license_header_common) template applies to the C, C++, Java source files.
* [license_header_other](../util/template/license_header_other) template applies to the Python and other source files.

These commands should be run at the root of the project.
