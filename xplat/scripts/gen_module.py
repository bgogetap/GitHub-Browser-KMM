import argparse
import os
from pathlib import Path

parser = argparse.ArgumentParser(description="Create a new library module")
parser.add_argument("-n", "--name", dest="name", help="Name of the module. Use `.` for separation to nest modules.")

PROJECT_ROOT = os.path.realpath(os.path.dirname(__file__)).rsplit('/', 1)[0]

MANIFEST_TEMPLATE = '''<manifest package="dev.neverstoplearning.githubbrowser.xplat.{module}"/>

'''

GRADLE_TEMPLATE_FILE = PROJECT_ROOT + "/templates/lib_module.gradle.kts"
PROGUARD_TEMPLATE_FILE = PROJECT_ROOT + "/templates/proguard-rules.pro"
GITIGNORE_TEMPLATE_FILE = PROJECT_ROOT + "/templates/.gitignore"

def add_file(dir, name):
    return open(dir + "/" + name, "w")

def add_manifest(dir):
    main_src_dir = dir + "/src/androidMain/"
    os.makedirs(main_src_dir, exist_ok=True)
    manifest_file = add_file(main_src_dir, "AndroidManifest.xml")
    manifest_value = MANIFEST_TEMPLATE.replace("{module}", args.name)
    manifest_file.write(manifest_value)

def add_gitignore_file(dir):
    gitignore_file = add_file(dir, ".gitignore")
    with open(GITIGNORE_TEMPLATE_FILE) as template_file:
        for line in template_file:
            gitignore_file.write(line)

def add_gradle_file(dir):
    gradle_file = add_file(dir, "build.gradle.kts")
    with open(GRADLE_TEMPLATE_FILE) as template_file:
        for line in template_file:
            gradle_file.write(line)

def add_proguard_file(dir):
    proguard_file = add_file(dir, "proguard-rules.pro")
    with open(PROGUARD_TEMPLATE_FILE) as template_file:
        for line in template_file:
            proguard_file.write(line)

def add_module_dirs(pieces):
    current = PROJECT_ROOT
    for piece in pieces:
        if len(piece) > 0:
            current = current + "/" + piece
            os.makedirs(current, exist_ok=True)
    return current

def add_src_dirs(dir, pieces, src_root):
    current = dir + "/src/" + src_root + "/kotlin/"
    for piece in pieces:
        if len(piece) > 0:
            current = current + "/" + piece
            os.makedirs(current, exist_ok=True)

args = parser.parse_args()

module_name = args.name

dirs_to_create = module_name.split(".")

module_dir = add_module_dirs(dirs_to_create)

add_proguard_file(module_dir)
add_manifest(module_dir)
add_gradle_file(module_dir)
add_gitignore_file(module_dir)

package_dirs_to_create = ["dev", "neverstoplearning", "githubbrowser", "xplat"] + dirs_to_create
add_src_dirs(module_dir, package_dirs_to_create, "androidMain")
add_src_dirs(module_dir, package_dirs_to_create, "androidTest")
add_src_dirs(module_dir, package_dirs_to_create, "commonMain")
add_src_dirs(module_dir, package_dirs_to_create, "commonTest")
add_src_dirs(module_dir, package_dirs_to_create, "iosMain")
add_src_dirs(module_dir, package_dirs_to_create, "iosTest")
