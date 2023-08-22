import openai
# Get OpenAI API key at https://platform.openai.com/account/api-keys
openai.api_key = "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"

completion = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[{"role": "user", "content": "Hello ChatGPT!"}]
)

print(completion.choices[0].message.content)
