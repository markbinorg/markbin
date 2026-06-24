<div align="center">
    <h1>Markbin</h1>
</div>

**Markbin** is a small markup language made so you can type bits and get a binary file!

Along with bits are special flags, useful if you don't plan to have unreadable source. Current flags may include:

| Label     | Description                                                   |
|-----------|---------------------------------------------------------------|
| `<bits>`  | Enabled by default, this flag switches the data mode to bits! |
| `<hex>`   | Switches the data mode to hex form!                           |
| `<utf8>`  | Encodes the following data as UTF-8 bytes.                    |
| `<utf16>` | Encodes the following data as UTF-16 bytes.                   |

Flags only affect data that comes after them!

You can find examples actually used during development inside the [examples](examples) directory! Below are showcases for some features:

---

`comments.mkb`
```mkb
01010101 ; The rest of this line is ignored!
```

`strings.mkb`
```mkb
"Anything can go inside strings, even ;, but it's important to remember strings will be ignored if outside of <utf8> for example"
<utf8>
"Yay I'm parsed as utf8!" ; Note comments outside strings are still ignored in all modes!
```

`text.mkb`
```mkb
; This applies to any text mode, but utf8 is common and a good example.
; As a reminder, all text here will be converted into its binary representation automatically.
<utf8>
Hey guys!        ; Wrong! This will look weird outside of quotes!
"That's better!" ; Strings are explained above.
<bits>           ; Switches back to bits mode!
01010101         ; UTF-8 representation of "U"
```

---
