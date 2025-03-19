# Huffman Coding File Compression Program

Implementation of Huffman coding for text file compression and decompression using Java.

## Features

- **Compression**: Compresses text files using Huffman coding
- **Decompression**: Restores compressed files to their original form
- **Visualization**: Displays the Huffman tree structure graphically

## File Format

The compressed file (.huff) follows this format:
```
[HUFF]                    - 4-byte magic number
[Num Chars: 4 bytes]      - Number of unique characters
[For each character:
  Char: 2 bytes          - Character code
  Code Length: 1 byte    - Length of Huffman code
  Code: variable bytes   - Huffman code (padded to byte boundary)]
[Data Length: 4 bytes]    - Length of compressed data in bits
[Compressed Data]         - Variable length bit stream
[Padding Bits: 1 byte]    - Number of padding bits in last byte
```