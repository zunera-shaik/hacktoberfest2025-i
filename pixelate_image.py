from PIL import Image

def pixelate_image(input_path, output_path, pixel_size=16):
    """Convert an image into a simple pixel-art style."""
    img = Image.open(input_path)

    #Shrinking the image
    small = img.resize(
        (img.width // pixel_size, img.height // pixel_size),
        resample=Image.NEAREST
    )

    #Scale back to original size
    pixel_art = small.resize(img.size, Image.NEAREST)

    # reduce colors for a more retro look
    pixel_art = pixel_art.convert("P", palette=Image.ADAPTIVE, colors=16)

    #Saving result
    pixel_art.save(output_path)
    print(f"✅ Pixel art saved to {output_path}")

# Example usage
if __name__ == "__main__":
    pixelate_image("sukuna-dope-amoled-5120x2880-16950.png", "pixel_art.png", pixel_size=12) #replace the name with the image you want
