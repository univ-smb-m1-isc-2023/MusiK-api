import acoustid as acid
import sys

API_KEY = "b'CHjabLfl"

# score, recording_id, title, artist = acid.match(
#    API_KEY, "./I_Keep_Forgetting.mp3")

try:
    # res = acid.match(API_KEY, "./I_Keep_Forgetting.mp3")
    # res = acid.fingerprint_file("Radio Ga Ga.mp3")
    res = acid.fingerprint_file(sys.argv[1])
    
    fingerprint = res[1].decode('utf-8')

    print( fingerprint )
    
    # response = acid.lookup(API_KEY, fingerprint, res[0])
    
    # print(response)
    
    # for score, recording_id, title, artist in res:
    #     print(score, recording_id, title, artist)
    # print("END")
except Exception as error:
    print("ERROR", error)
